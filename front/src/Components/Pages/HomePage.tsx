import Button from "../Comps/Button";
import TextField from "../Comps/TextField";
import {useNavigate} from "react-router-dom";
import UserDto from "../../Dtos/UserDto";
import connectHandler from "../../Connect/ConnectHandler";
import {useState} from "react";

/**
 * This is the login page/ banner page for the application where user can log in, if user is admin he/she will
 * go to admins main page not regular/business users main page.
 * If the user doesn't have a account he/she can register it in the register page.
 */
const HomePage = () => {
    const [name, setName] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [authenticated, setauthenticated] = useState(false);
    const navigate = useNavigate();


    /**
     * This function is for getting user info after he/she has filled in the username and login pages.
     * After user has been authenticated then he/she will be navigated to their main page.
     * @param username of the user
     * @param password of the user
     */
    const onClickHandle = async (username:string, password:string) => {
        let userDto: UserDto = {
            username: name,
            password: password,
            email: undefined,
            role: undefined,
            referenceUserUsername: undefined,
            maximum_items: undefined,
            cost: undefined,
            number_of_items: undefined,
            items_status: undefined
        }
        const resp = await connectHandler.getInfo("/user", userDto)
        if (resp.status === 200) {
            setauthenticated(true)
            userDto = resp.data
            if (userDto.role !== "ROLE_ADMIN") {
                navigate("/main", {state: {user: userDto}})
            } else {
                navigate("/adminMain", {state: {user: userDto}})

            }
        }
    }

    return (
        <div className={"grid-rows-1 gap-5  self-center justify-self-center content-center p-10  justify-centre bg-gray-500 w-2/3 h-2/3"}>

            <div><TextField value={name} setNumberValue={undefined} setStringValue={setName} id={"username"} type={'text'} placeholder={'Username'}/></div>
            <div><TextField value={password} setNumberValue={undefined} setStringValue={setPassword} id={"password"} type={'password'} placeholder={'Password'}/></div>
            <div onClick={(e:any) => onClickHandle(name, password)}><Button id={"login"}  name={"Login"}/></div>


            <div>If you don't have an account then register</div>
            <div onClick={() => navigate('/register')}> <Button id={"register"} name={"Register"}/></div>
        </div>

            )
}
export default HomePage;