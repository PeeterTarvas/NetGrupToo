interface UserDto {
    username: string;
    email: string|undefined;
    role: string|undefined;
    password: string;
    referenceUserUsername: string|undefined;
    maximum_items: number|undefined;
    cost: number|undefined;
    number_of_items: number|undefined;
}
export default UserDto;