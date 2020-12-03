package Entities;

import javafx.event.ActionEvent;

public class Seats {
    private int scheduleNo;
   private String busId;
   private boolean A1;
   private boolean A2;
   private boolean A3;
   private boolean A4;
   private boolean B1;
   private boolean B2;
   private boolean B3;
   private boolean B4;
   private boolean C1;
   private boolean C2;
   private boolean C3;
   private boolean C4;
   private boolean D1;
   private boolean D2;
   private boolean D3;
   private boolean D4;
   private boolean E1;
   private boolean E2;
   private boolean E3;
   private boolean E4;

    public Seats(int scheduleNo, String busId, boolean a1, boolean a2, boolean a3, boolean a4, boolean b1, boolean b2, boolean b3, boolean b4, boolean c1, boolean c2, boolean c3, boolean c4, boolean d1, boolean d2, boolean d3, boolean d4, boolean e1, boolean e2, boolean e3, boolean e4) {
        this.scheduleNo = scheduleNo;
        this.busId = busId;
        A1 = a1;
        A2 = a2;
        A3 = a3;
        A4 = a4;
        B1 = b1;
        B2 = b2;
        B3 = b3;
        B4 = b4;
        C1 = c1;
        C2 = c2;
        C3 = c3;
        C4 = c4;
        D1 = d1;
        D2 = d2;
        D3 = d3;
        D4 = d4;
        E1 = e1;
        E2 = e2;
        E3 = e3;
        E4 = e4;
    }

    public int getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(int scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public boolean isA1() {
        return A1;
    }

    public void setA1(boolean a1) {
        A1 = a1;
    }

    public boolean isA2() {
        return A2;
    }

    public void setA2(boolean a2) {
        A2 = a2;
    }

    public boolean isA3() {
        return A3;
    }

    public void setA3(boolean a3) {
        A3 = a3;
    }

    public boolean isA4() {
        return A4;
    }

    public void setA4(boolean a4) {
        A4 = a4;
    }

    public boolean isB1() {
        return B1;
    }

    public void setB1(boolean b1) {
        B1 = b1;
    }

    public boolean isB2() {
        return B2;
    }

    public void setB2(boolean b2) {
        B2 = b2;
    }

    public boolean isB3() {
        return B3;
    }

    public void setB3(boolean b3) {
        B3 = b3;
    }

    public boolean isB4() {
        return B4;
    }

    public void setB4(boolean b4) {
        B4 = b4;
    }

    public boolean isC1() {
        return C1;
    }

    public void setC1(boolean c1) {
        C1 = c1;
    }

    public boolean isC2() {
        return C2;
    }

    public void setC2(boolean c2) {
        C2 = c2;
    }

    public boolean isC3() {
        return C3;
    }

    public void setC3(boolean c3) {
        C3 = c3;
    }

    public boolean isC4() {
        return C4;
    }

    public void setC4(boolean c4) {
        C4 = c4;
    }

    public boolean isD1() {
        return D1;
    }

    public void setD1(boolean d1) {
        D1 = d1;
    }

    public boolean isD2() {
        return D2;
    }

    public void setD2(boolean d2) {
        D2 = d2;
    }

    public boolean isD3() {
        return D3;
    }

    public void setD3(boolean d3) {
        D3 = d3;
    }

    public boolean isD4() {
        return D4;
    }

    public void setD4(boolean d4) {
        D4 = d4;
    }

    public boolean isE1() {
        return E1;
    }

    public void setE1(boolean e1) {
        E1 = e1;
    }

    public boolean isE2() {
        return E2;
    }

    public void setE2(boolean e2) {
        E2 = e2;
    }

    public boolean isE3() {
        return E3;
    }

    public void setE3(boolean e3) {
        E3 = e3;
    }

    public boolean isE4() {
        return E4;
    }

    public void setE4(boolean e4) {
        E4 = e4;
    }

    @Override
    public String toString() {
        return "Seats{" +
                "scheduleNo=" + scheduleNo +
                ", busId='" + busId + '\'' +
                ", A1=" + A1 +
                ", A2=" + A2 +
                ", A3=" + A3 +
                ", A4=" + A4 +
                ", B1=" + B1 +
                ", B2=" + B2 +
                ", B3=" + B3 +
                ", B4=" + B4 +
                ", C1=" + C1 +
                ", C2=" + C2 +
                ", C3=" + C3 +
                ", C4=" + C4 +
                ", D1=" + D1 +
                ", D2=" + D2 +
                ", D3=" + D3 +
                ", D4=" + D4 +
                ", E1=" + E1 +
                ", E2=" + E2 +
                ", E3=" + E3 +
                ", E4=" + E4 +
                '}';
    }
}