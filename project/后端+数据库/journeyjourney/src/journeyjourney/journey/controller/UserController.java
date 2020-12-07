package journeyjourney.journey.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;

import journeyjourney.journey.model.User;



public class UserController extends Controller{

    public void index() {
        renderText("tbl_useController");
     }
    
    //注册新用户
    public void registerUser() {
        String  tel = getRequest().getParameter("tel");
        String  pwd = getRequest().getParameter("pwd");
        if(tel!=null&&pwd!=null) {
            boolean b = new User().register(tel,pwd);
            renderText(b+"");
        }else {
            renderText("false");
        }
    }
    
    //用户登录
    public void login() {
        String  tel = getRequest().getParameter("tel");
        String  pwd = getRequest().getParameter("pwd");
        if(tel!=null&&pwd!=null) {
            boolean b = new User().login(tel,pwd);
            renderText(b+"");
        }else {
            renderText("false");
        }
    }
    
    //上传头像
    public void upLoadImg() {
        String tel = getPara("tel");
        String fileType = getPara("fileType");
        String path  = PathKit.getWebRootPath();
        try {
            InputStream in = getRequest().getInputStream();
            OutputStream out = new FileOutputStream(path+"/userImg/"+tel+"."+fileType);
            boolean b = new User().upLoadImg(tel,in,out,fileType);
            renderText(b+"");
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }       
    }
    
    //我的界面
    public void my() {
        String tel = getPara("tel");
        if(tel!=null){
            String info = new User().my(tel);
            renderText(info);
        }else {
            renderText("no information");
        }
           
    }
       
    //修改用户信息
    public void updateMy() {
        String tel = getPara("tel");
        String name  = getPara("name");
        String sex  = getPara("sex");
        String intro = getPara("intro");
        if(tel!=null){
            String info = new User().updateMy(tel,name,sex,intro);
            renderText(info);
        }else {
            renderText("false");
        }
           
    }
    
    //修改密码
    public void updatePwd() {
        String  tel = getRequest().getParameter("tel");
        String  pwd = getRequest().getParameter("pwd");
        if(tel!=null&&pwd!=null) {
            boolean b = new User().updatePwd(tel,pwd);
            renderText(b+"");
        }else {
            renderText("false");
        } 
    }
    
    //我的作品
    public void  myWorks() {
        String tel = getPara("tel");
        String count = getPara("count");
        if(tel!=null){
            String info = new User().myWorks(tel,count);
            renderText(info);
        }else {
            renderText("no tel");
        }
        
    }
    
    //我的点赞
    public void myLoveWork() {
        String tel = getPara("tel");
        if(tel!=null){
            String info = new User().myLoveWork(tel);
            renderText(info);
        }else {
            renderText("no tel");
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}






