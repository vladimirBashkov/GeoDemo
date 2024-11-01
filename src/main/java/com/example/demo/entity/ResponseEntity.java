package com.example.demo.entity;

public class ResponseEntity {
    private Double[] startPos;
    private Double[] endPos;
    private String startAddress;
    private String endAddress;
    private long distance;

    public ResponseEntity() {
    }

    public ResponseEntity(Integer distance, String endAddress, Double[] endPos, String startAddress, Double[] startPos) {
        this.distance = distance;
        this.endAddress = endAddress;
        this.endPos = endPos;
        this.startAddress = startAddress;
        this.startPos = startPos;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public Double[] getEndPos() {
        return endPos;
    }

    public void setEndPos(Double[] endPos) {
        this.endPos = endPos;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public Double[] getStartPos() {
        return startPos;
    }

    public void setStartPos(Double[] startPos) {
        this.startPos = startPos;
    }
}
