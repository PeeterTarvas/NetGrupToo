import TextField from "../Comps/TextField";
import {Link} from "react-router-dom";
import React, {useState} from "react";

import UserDto from "../../Dtos/UserDto";
import connection from "../../Connect/ConnectHandler"

/**
 * This is a component for registering a user,
 */
const RegisterPage = () => {

    const [name, setName] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [email, setEmail] = useState<string>("")
    const [isBusiness, setIsBusiness] = useState<boolean>(false)
    const [referenceUserUsername, setReferenceUserUsername] = useState<string>("");

    /**
     * This is a function that handles the user registering a new user.
     * @param event
     */
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const user: UserDto = {
            username: name,
            password: password,
            email: email,
            role: isBusiness? 'ROLE_BUSINESS' : 'ROLE_USER',
            referenceUserUsername: referenceUserUsername,
            maximum_items: undefined,
            cost: undefined,
            number_of_items: undefined,
            items_status: undefined
        }
        connection.postInfo("/register/user", user)
    }

    const handleCheckbox = (event: React.ChangeEvent<HTMLInputElement>) => {
            setIsBusiness(current => !current);
    }


    return (
        <div className={"p-10 self-center content-center justify-center justify-self-center bg-gray-500 w-2/3 h-2/3"}>
        <form className="space-y-4 md:space-y-6" onSubmit={handleSubmit}>
            <div>
                <TextField value={name} setNumberValue={undefined} setStringValue={setName} id={"username"} type={'text'} placeholder={'Username'}/>
            </div>
            <div>
                <TextField value={password} setNumberValue={undefined} setStringValue={setPassword} id={"password"} type={'text'} placeholder={'Password'}/>

            </div>
            <div>
                <TextField value={email} setNumberValue={undefined} setStringValue={setEmail} id={"email"} type={'text'} placeholder={'Email'}/>
            </div>

            <div>
                <input id="default-checkbox" type="checkbox" onChange={handleCheckbox}
                       className="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"/>
                    <label htmlFor="default-checkbox"
                           className="ml-2 text-sm font-medium text-gray-900 dark:text-gray-300">Registering for business</label>
            </div>
            { isBusiness && <TextField setNumberValue={undefined} value={referenceUserUsername} setStringValue={setReferenceUserUsername} type={'text'} placeholder={'Enter reference user'} id={'reference_user'} />}

            <button
                type="submit"
                className={"bg-blue-500 w-1/3 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"}>
                {"Create"}
            </button>

            <p className="text-sm font-light text-gray-500 dark:text-gray-400">
                Already have an account?
            <Link to={"/"} ><p
                   className="font-medium text-primary-600 hover:underline dark:text-primary-500">Login
                here</p>
            </Link>
            </p>
        </form>
            </div>
    )
}
export default RegisterPage;