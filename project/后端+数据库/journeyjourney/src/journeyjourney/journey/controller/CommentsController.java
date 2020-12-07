package journeyjourney.journey.controller;

import com.jfinal.core.Controller;

import journeyjourney.journey.model.Comments;
import journeyjourney.journey.model.Note;

public class CommentsController extends Controller{

    public void index() {
        renderText("commentsController");
     }
    //评论
    public void oneComment() {
        String note_id = getPara("note_id");
        String comments_content = getPara("comments_content");
        String tel = getPara("tel");  
        String comments_time = getPara("comments_time");  
        if(note_id!=null) {
            String info = new Comments().oneComment(note_id,comments_content,tel,comments_time);
            renderText(info);         
        }else {
            renderText("no information");            
        }   
    }
    
    
    //展示评论
    public void showComment() {
        String note_id = getPara("note_id");
        if(note_id!=null) {
            String info = new Comments().showComment(note_id);
            renderText(info);         
        }else {
            renderText("no information");            
        }  
    }
    
    //子评论评论
    public void oneSonComment() {
        String note_id = getPara("note_id");
        String parentId = getPara("parentId");
        String comments_content = getPara("comments_content");
        String tel = getPara("tel");  
        String comments_time = getPara("comments_time");  
        if(parentId!=null) {
            String info = new Comments().oneSonComment(note_id,parentId,comments_content,tel,comments_time);
            renderText(info);         
        }else {
            renderText("no information");            
        }   
    }
    
    //展示子评论
    public void showSonComment() {
        String parentId = getPara("parentId");
        if(parentId!=null) {
            String info = new Comments().showSonComment(parentId);
            renderText(info);         
        }else {
            renderText("no information");            
        }  
    }
    
}