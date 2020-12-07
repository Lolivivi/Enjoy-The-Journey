package journeyjourney.journey.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;

import journeyjourney.journey.model.Note;
import journeyjourney.journey.model.User;


public class NoteController extends Controller{

    public void index() {
        renderText("noteController");
        
     }
    
    //首页界面
    public void homeInterface() {
        String tel = getPara("tel");
        String info = new Note().homeInterface(tel);
        renderText(info);
    }
    //搜索列表
    public void searchInterface() {
        String tel = getPara("tel");
        String param = getPara("param");
        String info = new Note().searchInterface(tel,param);
        renderText(info);
    }
    //关注页面
    public void followInterface() {
        String tel = getPara("tel");
        String count = getPara("count");
        if(tel!=null&&count!=null) {
            String info = new Note().followInterface(tel,count);
            renderText(info);         
        }else {
            renderText("no info");            
        }   
    }
    //推荐页面
    public void recommendInterface() {
        String tel = getPara("tel");
        String count = getPara("count");
        if(tel!=null) {
            String info = new Note().recommendInterface(tel,count);
            renderText(info);         
        }else {
            renderText("no tel");            
        }   
    }
    
    //同城页面
    public void sameCityInterface() {
        String city = getPara("city");
        String tel = getPara("tel");
        String count = getPara("count");
        if(city!=null) {
            String info = new Note().sameCityInterface(city,tel,count);
            renderText(info);         
        }else {
            renderText("no tel");            
        }   
    }
    
    //详情页
    public void detailsInterface() {
        String note_id = getPara("note_id");
        String tel = getPara("tel");
        if(note_id!=null) {
            String info = new Note().detailsInterface(note_id,tel);
            renderText(info);         
        }else {
            renderText("no note_id");            
        }   
    }
    

    //发布笔记9图
    public void upLoadNineImg() {
        String str = new Date().getTime()+"";
        
        String note_id = getPara("note_id");
        String fileType = getPara("fileType");
        String path  = PathKit.getWebRootPath();
        
        try {
            InputStream in = getRequest().getInputStream();
            OutputStream out = new FileOutputStream(path+"/smallImgs/"+str+"."+fileType);
            boolean b = new Note().upLoadNineImg(str,note_id,in,out,fileType);
            renderText(b+"");
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }       
    }

    //发布笔记首页图
    public void upLoadCoverImg() {
        String str = new Date().getTime()+"";
        
        String note_id = getPara("note_id");
        String fileType = getPara("fileType");
        String res_type = getPara("res_type");
        String path  = PathKit.getWebRootPath();
        
        try {
            InputStream in = getRequest().getInputStream();
            OutputStream out = new FileOutputStream(path+"/coverImg/"+str+"."+fileType);
            boolean b = new Note().upLoadCoverImg(str,note_id,in,out,fileType,res_type);
            renderText(b+"");
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }       
    }
    
    //发布笔记文字
    public void postNote() {
        String tel = getPara("tel");
        
        String titlle = getPara("titlle");
        
        String content = getPara("content");
        
        String upload_time = getPara("upload_time");
        
        String upload_position = getPara("upload_position");
 
        if(tel!=null) {
            String info = new Note().postNote(tel,titlle,content,upload_time,upload_position);
            renderText(info);         
        }else {
            renderText("no info");            
        } 
        
    } 
    

}


   




