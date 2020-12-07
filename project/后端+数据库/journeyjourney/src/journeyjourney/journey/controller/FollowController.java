package journeyjourney.journey.controller;

import com.jfinal.core.Controller;

import journeyjourney.journey.model.Follow;
import journeyjourney.journey.model.Note;

public class FollowController extends Controller{

    public void index() {
        renderText("followController");
     }
    
    //详情页关注
    public void detailsInterfaceFollow() {
        String fans_tel = getPara("fans_tel");
        String follow_tel = getPara("follow_tel");
        if(fans_tel!=null&&follow_tel!=null) {
            String info = new Note().detailsInterfaceFollow(fans_tel,follow_tel);
            renderText(info);         
        }else {
            renderText("no fans_tel or follow_tel");            
        }   
    }
    
    //详情页取消关注
    public void detailsInterfaceCancelFollow() {
        String fans_tel = getPara("fans_tel");
        String follow_tel = getPara("follow_tel");
        if(fans_tel!=null&&follow_tel!=null) {
            String info = new Note().detailsInterfaceCancelFollow(fans_tel,follow_tel);
            renderText(info);         
        }else {
            renderText("no fans_tel or follow_tel");            
        }   
    }
    
    //展示我的关注
    public void showMyFollows() {
        String tel = getPara("tel");
        if(tel!=null) {
            String info = new Follow().showMyFollows(tel);
            renderText(info);         
        }else {
            renderText("no tel");            
        } 
    }
}
