package com.eventpool.ticket.command.handler;
public interface CommandHandler<C, R> {

    public R handle(C command) throws Exception;
}