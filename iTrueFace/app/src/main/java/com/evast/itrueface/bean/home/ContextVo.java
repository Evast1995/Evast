package com.evast.itrueface.bean.home;

/**
 * Created by 72963 on 2015/12/11.
 */
public class ContextVo {
    private String imageUrl;
    private String courseName;
    private String teacherName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "ContextVo{" +
                "courseName='" + courseName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
