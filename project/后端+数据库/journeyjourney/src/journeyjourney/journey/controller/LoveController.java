package journeyjourney.journey.controller;

import com.jfinal.core.Controller;

import journeyjourney.journey.model.Note;

public class LoveController extends Controller{

    public void index() {
        renderText("loveController");
     }
    //关注页/详情页的点赞
    public void followInterfaceLove() {
        String tel = getPara("tel");
        String note_id = getPara("note_id");
        if(tel!=null&&note_id!=null) {
            String info = new Note().followInterfaceLove(tel,note_id);
            renderText(info);         
        }else {
            renderText("no fans_tel or note_id");            
        }               
    }
    
    //关注页/详情页的取消点赞
    public void followInterfaceCancelLove() {
        String tel = getPara("tel");
        String note_id = getPara("note_id");
        if(tel!=null&&note_id!=null) {
            String info = new Note().followInterfaceCancelLove(tel,note_id);
            renderText(info);         
        }else {
            renderText("no fans_tel or note_id");            
        }               
    }
}
