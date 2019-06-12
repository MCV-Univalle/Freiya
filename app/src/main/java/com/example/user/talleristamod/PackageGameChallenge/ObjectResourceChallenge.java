package com.example.user.talleristamod.PackageGameChallenge;

import com.google.firebase.storage.StorageReference;

public class ObjectResourceChallenge {

    public String resourceNameStudent , resourceNameStudentId, resourceId;



    public ObjectResourceChallenge() {}

    public ObjectResourceChallenge(String resourceNameStudent, String resourceNameStudentId) {
        this.resourceNameStudent = resourceNameStudent;
        this.resourceNameStudentId = resourceNameStudentId;

    }

    public ObjectResourceChallenge(String resourceNameStudent, String resourceNameStudentId, String resourceId)
    {
        this.resourceNameStudent = resourceNameStudent;
        this.resourceNameStudentId = resourceNameStudentId;
        this.resourceId = resourceId;

    }

    public String getResourceNameStudent() {
        return resourceNameStudent;
    }

    public void setResourceNameStudent(String resourceNameStudent) {
        this.resourceNameStudent = resourceNameStudent;
    }

    public String getResourceNameStudentId() {
        return resourceNameStudentId;
    }

    public void setResourceNameStudentId(String resourceNameStudentId) {
        this.resourceNameStudentId = resourceNameStudentId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId)
    {
        this.resourceId = resourceId;
    }


}
