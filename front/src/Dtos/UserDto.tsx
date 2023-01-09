/**
 * This is an interface object that is passed from front-end to back-end to send user information.
 */
interface UserDto {
    username: string;
    email: string|undefined;
    role: string|undefined;
    password: string;
    referenceUserUsername: string|undefined;
    maximum_items: number|undefined;
    cost: number|undefined;
    number_of_items: number|undefined;
    items_status: Object|undefined;
}
export default UserDto;