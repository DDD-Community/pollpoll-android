package com.ddd.pollpoll.core.model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b?\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u00a5\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\u0006\u0010\b\u001a\u00020\u0004\u0012\u0006\u0010\t\u001a\u00020\u0001\u0012\u0006\u0010\n\u001a\u00020\u0004\u0012\u0006\u0010\u000b\u001a\u00020\u0004\u0012\u0006\u0010\f\u001a\u00020\u0001\u0012\u0006\u0010\r\u001a\u00020\u0004\u0012\u0006\u0010\u000e\u001a\u00020\u0004\u0012\u0006\u0010\u000f\u001a\u00020\u0001\u0012\u0006\u0010\u0010\u001a\u00020\u0004\u0012\u0006\u0010\u0011\u001a\u00020\u0004\u0012\u0006\u0010\u0012\u001a\u00020\u0004\u0012\u0006\u0010\u0013\u001a\u00020\u0001\u0012\u0006\u0010\u0014\u001a\u00020\u0004\u0012\u0006\u0010\u0015\u001a\u00020\u0004\u0012\u0006\u0010\u0016\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0017J\t\u0010.\u001a\u00020\u0001H\u00c6\u0003J\t\u0010/\u001a\u00020\u0001H\u00c6\u0003J\t\u00100\u001a\u00020\u0004H\u00c6\u0003J\t\u00101\u001a\u00020\u0004H\u00c6\u0003J\t\u00102\u001a\u00020\u0001H\u00c6\u0003J\t\u00103\u001a\u00020\u0004H\u00c6\u0003J\t\u00104\u001a\u00020\u0004H\u00c6\u0003J\t\u00105\u001a\u00020\u0004H\u00c6\u0003J\t\u00106\u001a\u00020\u0001H\u00c6\u0003J\t\u00107\u001a\u00020\u0004H\u00c6\u0003J\t\u00108\u001a\u00020\u0004H\u00c6\u0003J\t\u00109\u001a\u00020\u0004H\u00c6\u0003J\t\u0010:\u001a\u00020\u0004H\u00c6\u0003J\t\u0010;\u001a\u00020\u0004H\u00c6\u0003J\t\u0010<\u001a\u00020\u0004H\u00c6\u0003J\t\u0010=\u001a\u00020\u0004H\u00c6\u0003J\t\u0010>\u001a\u00020\u0004H\u00c6\u0003J\t\u0010?\u001a\u00020\u0001H\u00c6\u0003J\t\u0010@\u001a\u00020\u0004H\u00c6\u0003J\t\u0010A\u001a\u00020\u0004H\u00c6\u0003J\u00d1\u0001\u0010B\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\t\u001a\u00020\u00012\b\b\u0002\u0010\n\u001a\u00020\u00042\b\b\u0002\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u00012\b\b\u0002\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u00042\b\b\u0002\u0010\u000f\u001a\u00020\u00012\b\b\u0002\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u00042\b\b\u0002\u0010\u0012\u001a\u00020\u00042\b\b\u0002\u0010\u0013\u001a\u00020\u00012\b\b\u0002\u0010\u0014\u001a\u00020\u00042\b\b\u0002\u0010\u0015\u001a\u00020\u00042\b\b\u0002\u0010\u0016\u001a\u00020\u0004H\u00c6\u0001J\u0013\u0010C\u001a\u00020D2\b\u0010E\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010F\u001a\u00020GH\u00d6\u0001J\t\u0010H\u001a\u00020\u0004H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001bR\u0011\u0010\b\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0011\u0010\t\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0011\u0010\n\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001bR\u0011\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001bR\u0011\u0010\f\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0019R\u0011\u0010\r\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001bR\u0011\u0010\u000e\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001bR\u0011\u0010\u000f\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0019R\u0011\u0010\u0010\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001bR\u0011\u0010\u0011\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001bR\u0011\u0010\u0012\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001bR\u0011\u0010\u0013\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0019R\u0011\u0010\u0014\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001bR\u0011\u0010\u0015\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010\u001bR\u0011\u0010\u0016\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001b\u00a8\u0006I"}, d2 = {"Lcom/ddd/pollpoll/core/model/Item;", "", "coFlag", "coGrade", "", "coValue", "dataTime", "khaiGrade", "khaiValue", "no2Flag", "no2Grade", "no2Value", "o3Flag", "o3Grade", "o3Value", "pm10Flag", "pm10Grade", "pm10Value", "sidoName", "so2Flag", "so2Grade", "so2Value", "stationName", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getCoFlag", "()Ljava/lang/Object;", "getCoGrade", "()Ljava/lang/String;", "getCoValue", "getDataTime", "getKhaiGrade", "getKhaiValue", "getNo2Flag", "getNo2Grade", "getNo2Value", "getO3Flag", "getO3Grade", "getO3Value", "getPm10Flag", "getPm10Grade", "getPm10Value", "getSidoName", "getSo2Flag", "getSo2Grade", "getSo2Value", "getStationName", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "core-model_debug"})
public final class Item {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Object coFlag = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String coGrade = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String coValue = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String dataTime = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String khaiGrade = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String khaiValue = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Object no2Flag = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String no2Grade = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String no2Value = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Object o3Flag = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String o3Grade = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String o3Value = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Object pm10Flag = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String pm10Grade = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String pm10Value = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String sidoName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Object so2Flag = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String so2Grade = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String so2Value = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String stationName = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.ddd.pollpoll.core.model.Item copy(@org.jetbrains.annotations.NotNull()
    java.lang.Object coFlag, @org.jetbrains.annotations.NotNull()
    java.lang.String coGrade, @org.jetbrains.annotations.NotNull()
    java.lang.String coValue, @org.jetbrains.annotations.NotNull()
    java.lang.String dataTime, @org.jetbrains.annotations.NotNull()
    java.lang.String khaiGrade, @org.jetbrains.annotations.NotNull()
    java.lang.String khaiValue, @org.jetbrains.annotations.NotNull()
    java.lang.Object no2Flag, @org.jetbrains.annotations.NotNull()
    java.lang.String no2Grade, @org.jetbrains.annotations.NotNull()
    java.lang.String no2Value, @org.jetbrains.annotations.NotNull()
    java.lang.Object o3Flag, @org.jetbrains.annotations.NotNull()
    java.lang.String o3Grade, @org.jetbrains.annotations.NotNull()
    java.lang.String o3Value, @org.jetbrains.annotations.NotNull()
    java.lang.Object pm10Flag, @org.jetbrains.annotations.NotNull()
    java.lang.String pm10Grade, @org.jetbrains.annotations.NotNull()
    java.lang.String pm10Value, @org.jetbrains.annotations.NotNull()
    java.lang.String sidoName, @org.jetbrains.annotations.NotNull()
    java.lang.Object so2Flag, @org.jetbrains.annotations.NotNull()
    java.lang.String so2Grade, @org.jetbrains.annotations.NotNull()
    java.lang.String so2Value, @org.jetbrains.annotations.NotNull()
    java.lang.String stationName) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public Item(@org.jetbrains.annotations.NotNull()
    java.lang.Object coFlag, @org.jetbrains.annotations.NotNull()
    java.lang.String coGrade, @org.jetbrains.annotations.NotNull()
    java.lang.String coValue, @org.jetbrains.annotations.NotNull()
    java.lang.String dataTime, @org.jetbrains.annotations.NotNull()
    java.lang.String khaiGrade, @org.jetbrains.annotations.NotNull()
    java.lang.String khaiValue, @org.jetbrains.annotations.NotNull()
    java.lang.Object no2Flag, @org.jetbrains.annotations.NotNull()
    java.lang.String no2Grade, @org.jetbrains.annotations.NotNull()
    java.lang.String no2Value, @org.jetbrains.annotations.NotNull()
    java.lang.Object o3Flag, @org.jetbrains.annotations.NotNull()
    java.lang.String o3Grade, @org.jetbrains.annotations.NotNull()
    java.lang.String o3Value, @org.jetbrains.annotations.NotNull()
    java.lang.Object pm10Flag, @org.jetbrains.annotations.NotNull()
    java.lang.String pm10Grade, @org.jetbrains.annotations.NotNull()
    java.lang.String pm10Value, @org.jetbrains.annotations.NotNull()
    java.lang.String sidoName, @org.jetbrains.annotations.NotNull()
    java.lang.Object so2Flag, @org.jetbrains.annotations.NotNull()
    java.lang.String so2Grade, @org.jetbrains.annotations.NotNull()
    java.lang.String so2Value, @org.jetbrains.annotations.NotNull()
    java.lang.String stationName) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Object component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Object getCoFlag() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCoGrade() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCoValue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDataTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getKhaiGrade() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getKhaiValue() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Object component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Object getNo2Flag() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNo2Grade() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNo2Value() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Object component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Object getO3Flag() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getO3Grade() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getO3Value() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Object component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Object getPm10Flag() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPm10Grade() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPm10Value() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSidoName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Object component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.Object getSo2Flag() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component18() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSo2Grade() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component19() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSo2Value() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component20() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStationName() {
        return null;
    }
}