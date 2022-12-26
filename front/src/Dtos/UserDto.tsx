interface UserDto {
    username: string;
    email: string|null;
    role: string|null;
    password: string,
    referenceUserUsername: string|null,

}
export default UserDto;