package com.support.my.room_db_poc.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by chers026 on 10/30/17.
 */

@Entity
public class Employee implements Parcelable {

    //We also have  @Embedded if we have another table that includes in Employee Table then we can add @Embedded for that table.

    @PrimaryKey
    @ColumnInfo(name = "userId")
    private String userId;
    private String jobTitleName;
    private String firstName;
    private String lastName;
    private String preferredFullName;
    private String employeeCode;
    private String region;
    private String phoneNumber;
    private String emailAddress;

    public Employee() {
        //
    }

    @Ignore
    public Employee(Builder builder) {
        this.userId = builder.userId;
        this.jobTitleName = builder.jobTitleName;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.preferredFullName = builder.preferredFullName;
        this.employeeCode = builder.employeeCode;
        this.region = builder.region;
        this.phoneNumber = builder.phoneNumber;
        this.emailAddress = builder.emailAddress;
    }

    @Ignore
    protected Employee(Parcel in) {
        userId = in.readString();
        jobTitleName = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        preferredFullName = in.readString();
        employeeCode = in.readString();
        region = in.readString();
        phoneNumber = in.readString();
        emailAddress = in.readString();
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public static Builder getBuilder() {
        return new Builder();
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJobTitleName() {
        return jobTitleName;
    }

    public void setJobTitleName(String jobTitleName) {
        this.jobTitleName = jobTitleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredFullName() {
        return preferredFullName;
    }

    public void setPreferredFullName(String preferredFullName) {
        this.preferredFullName = preferredFullName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userId);
        parcel.writeString(jobTitleName);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(preferredFullName);
        parcel.writeString(employeeCode);
        parcel.writeString(region);
        parcel.writeString(phoneNumber);
        parcel.writeString(emailAddress);
    }

    public static class Builder {

        private String userId;
        private String jobTitleName;
        private String firstName;
        private String lastName;
        private String preferredFullName;
        private String employeeCode;
        private String region;
        private String phoneNumber;
        private String emailAddress;

        public Employee build() throws NullPointerException, IllegalArgumentException {
            return new Employee(this);
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setJobTitleName(String jobTitleName) {
            this.jobTitleName = jobTitleName;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setPreferredFullName(String preferredFullName) {
            this.preferredFullName = preferredFullName;
            return this;
        }

        public Builder setEmployeeCode(String employeeCode) {
            this.employeeCode = employeeCode;
            return this;
        }

        public Builder setRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }
    }
}
