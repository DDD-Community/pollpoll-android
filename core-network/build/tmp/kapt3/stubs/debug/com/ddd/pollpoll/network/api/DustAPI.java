package com.ddd.pollpoll.network.api;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001JC\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u00052\b\b\u0001\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\b2\b\b\u0001\u0010\n\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2 = {"Lcom/ddd/pollpoll/network/api/DustAPI;", "", "fetchDustList", "Lcom/ddd/pollpoll/core/model/Dust;", "serviceKey", "", "returnType", "numOfRows", "", "pageNo", "sidoName", "(Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "core-network_debug"})
public abstract interface DustAPI {
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "/getCtprvnRltmMesureDnsty")
    public abstract java.lang.Object fetchDustList(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Field(value = "serviceKey")
    java.lang.String serviceKey, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Field(value = "returnType")
    java.lang.String returnType, @retrofit2.http.Field(value = "numOfRows")
    int numOfRows, @retrofit2.http.Field(value = "pageNo")
    int pageNo, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Field(value = "sidoName")
    java.lang.String sidoName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.ddd.pollpoll.core.model.Dust> continuation);
}