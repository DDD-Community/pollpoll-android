package com.ddd.pollpoll.network.remotesource;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J)\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\n"}, d2 = {"Lcom/ddd/pollpoll/network/remotesource/DustRemoteSource;", "", "fetchDustList", "Lcom/ddd/pollpoll/core/model/Dust;", "numOfRows", "", "pageNo", "sidoName", "", "(IILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "core-network_debug"})
public abstract interface DustRemoteSource {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object fetchDustList(int numOfRows, int pageNo, @org.jetbrains.annotations.NotNull()
    java.lang.String sidoName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.ddd.pollpoll.core.model.Dust> continuation);
}