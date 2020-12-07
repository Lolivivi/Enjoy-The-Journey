package journeyjourney.journey.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseOnlineTravel<M extends BaseOnlineTravel<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setTitle(java.lang.String title) {
		set("title", title);
		return (M)this;
	}
	
	public java.lang.String getTitle() {
		return getStr("title");
	}

	public M setIntro(java.lang.String intro) {
		set("intro", intro);
		return (M)this;
	}
	
	public java.lang.String getIntro() {
		return getStr("intro");
	}

	public M setImg1(java.lang.String img1) {
		set("img1", img1);
		return (M)this;
	}
	
	public java.lang.String getImg1() {
		return getStr("img1");
	}

	public M setImg2(java.lang.String img2) {
		set("img2", img2);
		return (M)this;
	}
	
	public java.lang.String getImg2() {
		return getStr("img2");
	}

	public M setImg3(java.lang.String img3) {
		set("img3", img3);
		return (M)this;
	}
	
	public java.lang.String getImg3() {
		return getStr("img3");
	}

	public M setLatLong1(java.lang.String latLong1) {
		set("latLong1", latLong1);
		return (M)this;
	}
	
	public java.lang.String getLatLong1() {
		return getStr("latLong1");
	}

	public M setLatLong2(java.lang.String latLong2) {
		set("latLong2", latLong2);
		return (M)this;
	}
	
	public java.lang.String getLatLong2() {
		return getStr("latLong2");
	}

	public M setLatLong3(java.lang.String latLong3) {
		set("latLong3", latLong3);
		return (M)this;
	}
	
	public java.lang.String getLatLong3() {
		return getStr("latLong3");
	}

}
