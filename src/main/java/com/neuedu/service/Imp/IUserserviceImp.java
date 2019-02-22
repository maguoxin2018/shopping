package com.neuedu.service.Imp;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.redis.RedisApi;
import com.neuedu.redis.RedisPool;
import com.neuedu.redis.RedisProperties;
import com.neuedu.service.IUserservice;
import com.neuedu.utils.MD5Utils;
import com.neuedu.utils.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

@Service
public class IUserserviceImp implements IUserservice {
    @Autowired
    UserInfoMapper  UserInfoMapper;
    @Autowired
    RedisApi redisApi;
    @Autowired
    RedisProperties redisProperties;


//  登录
    @Override
    public ServerResponse login(String username, String password) {
        //step:1    非空校验
        if (username==null || username.equals("")){
            return ServerResponse.createServerResponseByFail("用户名不能为空");
        }
        if (password==null || password.equals("")){
            return ServerResponse.createServerResponseByFail("密码不能为空");
        }
        //step:2     验证用户名是否存在
        int checkusernameisexist = UserInfoMapper.checkusernameisexist(username);
        if (checkusernameisexist==0){
            return ServerResponse.createServerResponseByFail("用户名不存在");
        }
        //setp:3     通过用户名和密码查找用户信息，返回查询结果
        UserInfo userInfo = UserInfoMapper.selectuserinfoByusernameAndpassword(username,MD5Utils.getMD5Code(password));
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail("密码错误");
        }
        //生成token
        String token = MD5Utils.getMD5Code(username+password);
        userInfo.setToken(token);
        int i = UserInfoMapper.updateByPrimaryKey(userInfo);
        if (i<0){
            System.out.println("更新token失败");
            return ServerResponse.createServerResponseByFail("更新token失败");
        }
        userInfo.setPassword("");
        System.out.println(redisProperties.getMaxIdle());
        return ServerResponse.createServerResponseBySuccess(userInfo,null);
    }



//     注册
    @Override
    public ServerResponse register(UserInfo userInfo) {
        //step:1    非空校验
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail("信息不能为空");
        }
        //step:2     验证用户名是否存在
        int checkusernameisexist = UserInfoMapper.checkusernameisexist(userInfo.getUsername());
        if (checkusernameisexist > 0){
            return ServerResponse.createServerResponseByFail("用户名已经存在");
        }
        //step:3    校验邮箱是否存在
        int checkemailisexist = UserInfoMapper.checkusernameisexist(userInfo.getEmail());
        if (checkemailisexist > 0){
            return ServerResponse.createServerResponseByFail("邮箱已经存在");
        }
        //step:4    注册
        userInfo.setRole(Const.RoleEnum.ROLE_CUSTOMER.getCode());
        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        int insert = UserInfoMapper.insert(userInfo);
        if (insert > 0){
            return ServerResponse.createServerResponseBySuccess("注册成功");
        }
        //step:5    返回结果
        return ServerResponse.createServerResponseByFail("注册失败");
    }




//   找回密码
    @Override
    public ServerResponse forget_get_question(String username) {
//        setp：1   参数校验
        if (username == null || username.equals("")){
            return ServerResponse.createServerResponseByFail("用户名不能为空");
        }
//        setp：2  校验username
        int checkusernameisexist = UserInfoMapper.checkusernameisexist(username);
        if (checkusernameisexist == 0){
            return ServerResponse.createServerResponseByFail("用户不存在，重新输入");
        }
        String question = UserInfoMapper.checkquestionByusername(username);
        if (question ==null || question.equals("")){
            return ServerResponse.createServerResponseByFail("密保问题为空");
        }
        return ServerResponse.createServerResponseBySuccess(question);
    }



//      找回密码
    @Override
    public ServerResponse forget_check_answer(String username, String question, String answer) {
//        setp:1   参数校验
        if (username == null ||username.equals("")){
            return ServerResponse.createServerResponseByFail("用户名不能为空");
        }
        if (question == null ||question.equals("")){
            return ServerResponse.createServerResponseByFail("问题不能为空");
        }
        if (answer == null ||answer.equals("")){
            return ServerResponse.createServerResponseByFail("答案不能为空");
        }
        //        setp:2   根据用户名，问题，答案查询用户信息
        int i = UserInfoMapper.selectuserinfoByusernameAndquestionAndanswer(username, question, answer);
        if (i == 0){
            return ServerResponse.createServerResponseByFail("答案错误");
        }
        //        setp:2   服务端生成一个token保存并将它传给客户端
        String forgettoken = UUID.randomUUID().toString();
//        TokenCache.set(username,forgettoken);
        redisApi.set(username,forgettoken);
        return ServerResponse.createServerResponseBySuccess(forgettoken,null);
    }




    @Override
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgettoken) {
//       setp：1  参数校验
        if (username == null ||username.equals("")){
            return ServerResponse.createServerResponseByFail("用户名不能为空");
        }
        if (passwordNew == null ||passwordNew.equals("")){
            return ServerResponse.createServerResponseByFail("密码不能为空");
        }
        if (forgettoken == null ||forgettoken.equals("")){
            return ServerResponse.createServerResponseByFail("token不能为空");
        }
//       setp：2  token校验
//        String s = TokenCache.get(username);
        String s = redisApi.get(username);
        if (s ==null){
            return ServerResponse.createServerResponseByFail("token过期");
        }
        if (!s.equals(forgettoken)){
            return ServerResponse.createServerResponseByFail("无效的token");
        }
//       setp：3  修改密码
        int i = UserInfoMapper.updateUserPassword(username,MD5Utils.getMD5Code(passwordNew));
        if (i > 0 ){
            return ServerResponse.createServerResponseBySuccess();
        }
        return ServerResponse.createServerResponseByFail("密码修改失败");
    }
//      检查用户名和密码是否有效
    @Override
    public ServerResponse check_valid(String str, String type) {
//        setp：1  参数校验
        if (str == null ||str.equals("")){
            return ServerResponse.createServerResponseByFail("用户名或者邮箱不能为空");
        }
        if (type == null ||type.equals("")){
            return ServerResponse.createServerResponseByFail("校验的类型参数不能为空");
        }
//        setp：2  type ： username
//                            email
        if (type.equals("username")){
            int checkusernameisexist = UserInfoMapper.checkusernameisexist(str);
            if (checkusernameisexist>0){
                return ServerResponse.createServerResponseByFail("用户名已经存在");
            }else {
                return ServerResponse.createServerResponseBySuccess("");
            }
        }else if (type.equals("email")){
            int checkemailisexist = UserInfoMapper.checkemailisexist(str);
            if (checkemailisexist>0){
                return ServerResponse.createServerResponseByFail("邮箱已经存在");
            }else {
                return ServerResponse.createServerResponseBySuccess("");
            }
        }else{
            return ServerResponse.createServerResponseByFail("参数错误");
        }
    }

    @Override
    public ServerResponse reset_password(String username,String passwordOld, String passwordNew) {
//        setp：1  参数校验
        if (passwordOld == null ||passwordOld.equals("")){
            return ServerResponse.createServerResponseByFail("用户旧密码不能为空");
        }
        if (passwordNew == null ||passwordNew.equals("")){
            return ServerResponse.createServerResponseByFail("用户新密码不能为空");
        }
//       setp：2  根据用户名密码，更改新密码
        UserInfo userInfo = UserInfoMapper.selectuserinfoByusernameAndpassword(username, MD5Utils.getMD5Code(passwordOld));
        if (userInfo ==null){
            return ServerResponse.createServerResponseByFail("旧密码错误");
        }
//      setp:3   修改密码
        userInfo.setPassword(MD5Utils.getMD5Code(passwordNew));
        int i = UserInfoMapper.updateByPrimaryKey(userInfo);
        if (i > 0){
            return ServerResponse.createServerResponseBySuccess("修改密码成功");
        }
        return ServerResponse.createServerResponseByFail("修改密码失败");
    }

    @Override
    public ServerResponse update_information(UserInfo user) {
//        setp：1  参数校验
        if(user ==null){
            return ServerResponse.createServerResponseByFail("参数不能为空");
        }
//       setp：2   更新信息
        int i = UserInfoMapper.updateUsermessage(user);
        if (i > 0){
            return ServerResponse.createServerResponseBySuccess("修改信息成功");
        }
        return ServerResponse.createServerResponseByFail("修改信息失败");
    }
//      根据用户id查新个人信息
    @Override
    public UserInfo selectUserByUserid(Integer userId) {
        return UserInfoMapper.selectByPrimaryKey(userId);
    }



//      查询token
    @Override
    public ServerResponse findInfoByToken(String token) {
        UserInfo infoByToken = UserInfoMapper.findInfoByToken(token);
        if (infoByToken==null||infoByToken.equals("")){
            return ServerResponse.createServerResponseByFail("失败");
        }
        return ServerResponse.createServerResponseBySuccess(infoByToken,null);
    }
}
