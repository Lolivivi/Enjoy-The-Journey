package journeyjourney.journey.controller;

import com.jfinal.core.Controller;

/**
 * Generated by JFinal.
 * date:2020-11-26
 * author:yyl
 */
import journeyjourney.journey.model.Journeyonline;
public class JourneyOnlineController extends Controller{
    
     public void index() {
            renderText("journeyOnlineController");
         }
        
        //线上旅游界面
        public void journeyOnline() {
            String info = new Journeyonline().journeyOnline();
            renderText(info);
        }
}