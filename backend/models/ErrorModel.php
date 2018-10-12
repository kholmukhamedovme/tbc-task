<?php

/**
 * Class ErrorModel
 *
 * Codes:
 * 1 - Value for required field is not provided
 * 2 - Format for profile picture is not supported
 * 3 - Error code for operations with files
 * 4 - Error code for operations with database
 */
class ErrorModel
{
    const REQUIRED_FIELD_NOT_PROVIDED = 1;
    const FORMAT_IS_NOT_SUPPORTED = 2;
    const FILE_OPERATION_ERROR = 3;
    const DB_OPERATION_ERROR = 4;

    public $code;
    public $message;

    /**
     * ErrorModel constructor
     *
     * @param int $code error code
     * @param string $message error message
     */
    public function __construct(int $code, string $message)
    {
        $this->code = $code;
        $this->message = $message;
    }
}
