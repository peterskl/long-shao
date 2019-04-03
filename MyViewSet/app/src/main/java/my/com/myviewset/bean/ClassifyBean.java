package my.com.myviewset.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/12/22.
 */

public class ClassifyBean implements Parcelable {

    /**
     * id : 12
     * categoryName : 家居百货
     * isDel : 0
     * isRecommend : 0
     * sorted : 0
     * pid : 1
     * img : ""
     * words : 羽绒服,棉衣/棉服
     * maleImg : "https://imgs.gmilesquan.com//MBNNFMeQ.png"
     */

    private int id;
    private String categoryName;
    private int isDel;
    private int isRecommend;
    private int sorted;
    private int pid;
    private String img;
    private String words;
    private int maleImg;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public int getSorted() {
        return sorted;
    }

    public void setSorted(int sorted) {
        this.sorted = sorted;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public int getMaleImg() {
        return maleImg;
    }

    public void setMaleImg(int maleImg) {
        this.maleImg = maleImg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.categoryName);
        dest.writeInt(this.isDel);
        dest.writeInt(this.isRecommend);
        dest.writeInt(this.sorted);
        dest.writeInt(this.pid);
        dest.writeString(this.words);
        dest.writeInt(this.maleImg);
    }

    public ClassifyBean(String categoryName,int maleImg) {
        this.categoryName = categoryName;
        this.maleImg = maleImg;
    }

    protected ClassifyBean(Parcel in) {
        this.id = in.readInt();
        this.categoryName = in.readString();
        this.isDel = in.readInt();
        this.isRecommend = in.readInt();
        this.sorted = in.readInt();
        this.pid = in.readInt();
        this.words = in.readString();
        this.maleImg = in.readInt();
    }

    public static final Creator<ClassifyBean> CREATOR = new Creator<ClassifyBean>() {
        @Override
        public ClassifyBean createFromParcel(Parcel source) {
            return new ClassifyBean(source);
        }

        @Override
        public ClassifyBean[] newArray(int size) {
            return new ClassifyBean[size];
        }
    };
}
