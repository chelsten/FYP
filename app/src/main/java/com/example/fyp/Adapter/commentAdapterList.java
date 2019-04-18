package com.example.fyp.Adapter;


public class commentAdapterList {
    private String  full_name;
    private String farmerPost;
    private String pic;
    private String comment_id;
    public commentAdapterList(String full_name, String farmerPost, String pic, String comment_id) {
        this.full_name = full_name;
        this.farmerPost = farmerPost;
        this.pic = pic;
        this.comment_id = comment_id;
    }

    public String getFull_name() {
        return full_name;
    }
    public String getFarmerPost(){
        return farmerPost;
    }
    public String getPic() {
        return pic;
    }
    public String getCommentID(){return comment_id;}

}