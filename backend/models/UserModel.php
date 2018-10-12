<?php

/**
 * Class UserModel
 */
class UserModel
{
    public $id;
    public $name;
    public $hasPhoto = false;
    public $status = false;

    public function __construct()
    {
    }

    public static function fromValues(int $id, string $name, bool $hasPhoto = false, bool $status = false): UserModel
    {
        $user = new UserModel();

        $user->id = $id;
        $user->name = $name;
        $user->hasPhoto = $hasPhoto;
        $user->status = $status;

        return $user;
    }

    public static function fromArray($array)
    {
        $user = new UserModel();

        $user->id = intval($array["id"]);
        $user->name = $array["name"];
        $user->hasPhoto = boolval($array["hasPhoto"]);
        $user->status = boolval($array["status"]);

        return $user;
    }
}
