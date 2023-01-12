/**
 * This is an interface object that is passed from front-end to back-end to send user information.
 */
interface UserDto {
    username: string;
    email: string|undefined;
    role: string|undefined;
    password: string;
    referenceUserUsername: string|undefined;
    maximumItems: number|undefined;
    cost: number|undefined;
    numberOfItems: number|undefined;
    itemsStatus: Object|undefined;
}
export default UserDto;