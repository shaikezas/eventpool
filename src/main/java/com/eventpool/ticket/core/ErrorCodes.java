package com.eventpool.ticket.core;
public interface ErrorCodes {

    String _NAMESPACE_ = "inventory.api.server.";

    String FAILED_TO_PROCESS_COMMAND = _NAMESPACE_ + "FailedToProcessCommand";
    String COMMAND_HANDLER_NOT_FOUND = _NAMESPACE_ + "CommandHandlerNotFound";
}