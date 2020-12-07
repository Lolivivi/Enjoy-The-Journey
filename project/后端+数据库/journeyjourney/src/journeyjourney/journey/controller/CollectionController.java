package journeyjourney.journey.controller;

import com.jfinal.core.Controller;

import journeyjourney.journey.model.Note;

public class CollectionController extends Controller{

    public void index() {
        renderText("collectionController");
     }
    
    //展示收藏夹
    public void ShowCollectionDirectory() {
        String tel = getPara("tel");
        if(tel!=null) {
            String info = new Note().ShowCollectionDirectory(tel);
            renderText(info);         
        }else {
            renderText("no tel");            
        } 
        
    }
    
    //添加收藏夹
    public void addCollectionDirectory() {
        String tel = getPara("tel");
        String name = getPara("name");
        if(tel!=null) {
            String info = new Note().addCollectionDirectory(tel,name);
            renderText(info);         
        }else {
            renderText("no tel");            
        } 
    }
    
    //删除收藏夹
    public void deleteCollectionDirectory() {
        String tel = getPara("tel");
        String name = getPara("name");
        if(tel!=null) {
            String info = new Note().deleteCollectionDirectory(tel,name);
            renderText(info);         
        }else {
            renderText("no tel");            
        } 
    }
    
    //展示某个收藏夹里所有笔记
    public void showOneDirectory() {
        String tel = getPara("tel");
        String name = getPara("name");
        if(tel!=null) {
            String info = new Note().showOneDiretrory(tel,name);
            renderText(info);         
        }else {
            renderText("no tel");            
        } 
    }
    //将笔记加入到收藏夹
    public void addOneNoteToDirectory() {
        String note_id = getPara("note_id");
        String tel = getPara("tel");
        String name = getPara("name");
        if(tel!=null&&note_id!=null&&name!=null) {  
            String info = new Note().addOneNoteToDirectory(note_id,tel,name);
            renderText(info);         
        }else {
            renderText("no information");            
        } 
    }
    
    //将笔记加从收藏夹取消收藏
    public void deleteOneNoteToDirectory() {
        String note_id = getPara("note_id");
        String tel = getPara("tel");
        if(tel!=null&&note_id!=null) {  
            String info = new Note().deleteOneNoteToDirectory(note_id,tel);
            renderText(info);         
        }else {
            renderText("no information");            
        } 
    }
    
    
}
