package journeyjourney.journey.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseNote<M extends BaseNote<M>> extends Model<M> implements IBean {

	public M setNoteId(java.lang.Integer noteId) {
		set("note_id", noteId);
		return (M)this;
	}
	
	public java.lang.Integer getNoteId() {
		return getInt("note_id");
	}

	public M setTel(java.lang.String tel) {
		set("tel", tel);
		return (M)this;
	}
	
	public java.lang.String getTel() {
		return getStr("tel");
	}

	public M setCoverSrc(java.lang.String coverSrc) {
		set("cover_src", coverSrc);
		return (M)this;
	}
	
	public java.lang.String getCoverSrc() {
		return getStr("cover_src");
	}

	public M setLikeNum(java.lang.Integer likeNum) {
		set("like_num", likeNum);
		return (M)this;
	}
	
	public java.lang.Integer getLikeNum() {
		return getInt("like_num");
	}

	public M setCollectionNum(java.lang.Integer collectionNum) {
		set("collection_num", collectionNum);
		return (M)this;
	}
	
	public java.lang.Integer getCollectionNum() {
		return getInt("collection_num");
	}

	public M setCommentsNum(java.lang.Integer commentsNum) {
		set("comments_num", commentsNum);
		return (M)this;
	}
	
	public java.lang.Integer getCommentsNum() {
		return getInt("comments_num");
	}

	public M setTitlle(java.lang.String titlle) {
		set("titlle", titlle);
		return (M)this;
	}
	
	public java.lang.String getTitlle() {
		return getStr("titlle");
	}

	public M setContent(java.lang.String content) {
		set("content", content);
		return (M)this;
	}
	
	public java.lang.String getContent() {
		return getStr("content");
	}

	public M setUploadTime(java.lang.String uploadTime) {
		set("upload_time", uploadTime);
		return (M)this;
	}
	
	public java.lang.String getUploadTime() {
		return getStr("upload_time");
	}

	public M setUploadPosition(java.lang.String uploadPosition) {
		set("upload_position", uploadPosition);
		return (M)this;
	}
	
	public java.lang.String getUploadPosition() {
		return getStr("upload_position");
	}

	public M setResType(java.lang.String resType) {
		set("res_type", resType);
		return (M)this;
	}
	
	public java.lang.String getResType() {
		return getStr("res_type");
	}

	public M setModleId(java.lang.String modleId) {
		set("modle_id", modleId);
		return (M)this;
	}
	
	public java.lang.String getModleId() {
		return getStr("modle_id");
	}

	public M setTagId(java.lang.Integer tagId) {
		set("tag_id", tagId);
		return (M)this;
	}
	
	public java.lang.Integer getTagId() {
		return getInt("tag_id");
	}

}
