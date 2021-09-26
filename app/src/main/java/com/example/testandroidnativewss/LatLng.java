package com.example.testandroidnativewss;

import android.os.Parcel;
import android.os.Parcelable;

public class LatLng implements Parcelable {

    private int id;
    private double lat;
    private double lng;

    public LatLng() {
        super();
    }

    private LatLng(Parcel in) {
        super();
        this.id = in.readInt();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "LatLng [id=" + id + ", lat=" + lat + ", lng=" + lng + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LatLng other = (LatLng) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeDouble(getLat());
        parcel.writeDouble(getLng());
    }

    public static final Parcelable.Creator<LatLng> CREATOR = new Parcelable.Creator<LatLng>() {
        public LatLng createFromParcel(Parcel in) {
            return new LatLng(in);
        }

        public LatLng[] newArray(int size) {
            return new LatLng[size];
        }
    };

}