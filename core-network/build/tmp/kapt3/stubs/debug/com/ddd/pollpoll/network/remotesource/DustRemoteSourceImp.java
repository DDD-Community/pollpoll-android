package com.ddd.pollpoll.network.remotesource;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J)\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, d2 = {"Lcom/ddd/pollpoll/network/remotesource/DustRemoteSourceImp;", "Lcom/ddd/pollpoll/network/remotesource/DustRemoteSource;", "api", "Lcom/ddd/pollpoll/network/api/DustAPI;", "(Lcom/ddd/pollpoll/network/api/DustAPI;)V", "fetchDustList", "Lcom/ddd/pollpoll/core/model/Dust;", "numOfRows", "", "pageNo", "sidoName", "", "(IILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "core-network_debug"})
public final class DustRemoteSourceImp implements com.ddd.pollpoll.network.remotesource.DustRemoteSource {
    private final com.ddd.pollpoll.network.api.DustAPI api = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.ddd.pollpoll.network.remotesource.DustRemoteSourceImp.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String API_KEY = "LL0SSOpdVJrjj%2FOgFhTFxrEKFzVyJxrCKUyNncefeNLGGocos9LR2BNrSRB5RlWI1F0yqEfIrGMOY26ccNZ7fA%3D%3D";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String RETURN_TYPE = "json";
    
    @javax.inject.Inject()
    public DustRemoteSourceImp(@org.jetbrains.annotations.NotNull()
    com.ddd.pollpoll.network.api.DustAPI api) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object fetchDustList(int numOfRows, int pageNo, @org.jetbrains.annotations.NotNull()
    java.lang.String sidoName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.ddd.pollpoll.core.model.Dust> continuation) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/ddd/pollpoll/network/remotesource/DustRemoteSourceImp$Companion;", "", "()V", "API_KEY", "", "RETURN_TYPE", "core-network_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}