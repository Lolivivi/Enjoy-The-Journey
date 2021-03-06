package journeyjourney.journey.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseImgs<M extends BaseImgs<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setNodeId(java.lang.Integer nodeId) {
		set("node_id", nodeId);
		return (M)this;
	}
	
	public java.lang.Integer getNodeId() {
		return getInt("node_id");
	}

	public M setImgSrc(java.lang.String imgSrc) {
		set("img_src", imgSrc);
		return (M)this;
	}
	
	public java.lang.String getImgSrc() {
		return getStr("img_src");
	}

}
