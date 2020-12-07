package journeyjourney.journey;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;

import journeyjourney.journey.controller.CollectionController;
import journeyjourney.journey.controller.CommentsController;
import journeyjourney.journey.controller.FollowController;
import journeyjourney.journey.controller.ImgsController;
import journeyjourney.journey.controller.JourneyOnlineController;
import journeyjourney.journey.controller.LoveController;
import journeyjourney.journey.controller.NoteController;
import journeyjourney.journey.controller.PlanController;
import journeyjourney.journey.controller.UserController;
import journeyjourney.journey.model.Collection;
import journeyjourney.journey.model.Comments;
import journeyjourney.journey.model.Follow;
import journeyjourney.journey.model.Food;
import journeyjourney.journey.model.Imgs;
import journeyjourney.journey.model.Journeyonline;
import journeyjourney.journey.model.Love;
import journeyjourney.journey.model.Modle;
import journeyjourney.journey.model.Note;
import journeyjourney.journey.model.Plan;
import journeyjourney.journey.model.Route;
import journeyjourney.journey.model.SonComment;
import journeyjourney.journey.model.Tag;
import journeyjourney.journey.model.User;

public class DemoConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/collection", CollectionController.class);
        me.add("/comments", CommentsController.class);
        me.add("/follow", FollowController.class);
        me.add("/imgs", ImgsController.class);
        me.add("/love", LoveController.class);
        me.add("/note", NoteController.class);
        me.add("/plan", PlanController.class);
        me.add("/user", UserController.class);
        me.add("/journeyonline", JourneyOnlineController.class);
    }

    @Override
    public void configEngine(Engine me) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void configPlugin(Plugins me) {
        DruidPlugin dp = new DruidPlugin("jdbc:mysql://localhost/enjoy-the-journey", "root", "123456");
        me.add(dp);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        me.add(arp);
        
        //arp.setShowSql(true);//这句话就是ShowSql
        arp.addMapping("tbl_collection", "name,note_id,tel", Collection.class);
        arp.addMapping("tbl_comments", "id", Comments.class);
        arp.addMapping("tbl_follow", "id", Follow.class);
        arp.addMapping("tbl_food", "id", Food.class);
        arp.addMapping("tbl_imgs", "id", Imgs.class);
        // Composite Primary Key order: note_id,tel
        arp.addMapping("tbl_love", "note_id,tel", Love.class);
        arp.addMapping("tbl_modle", "id", Modle.class);
        arp.addMapping("tbl_note", "note_id", Note.class);
        arp.addMapping("tbl_plan", "id", Plan.class);
        arp.addMapping("tbl_route", "id", Route.class);
        arp.addMapping("tbl_tag", "tag_id", Tag.class);
        arp.addMapping("tbl_user", "tel", User.class);
        arp.addMapping("tbl_journeyonline", "id", Journeyonline.class);
        arp.addMapping("tbl_son_comment", "id", SonComment.class);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void configHandler(Handlers me) {
        // TODO Auto-generated method stub
        
    }

}
