import Button from "../Comps/Button";
import TextField from "../Comps/TextField";
import {useContext, useState} from "react";
import {useNavigate} from "react-router-dom";
import UserDto from "../../Dtos/UserDto";
import connectHandler from "../../Connect/ConnectHandler";

const HomePage = () => {
    const [name, setName] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [authenticated, setauthenticated] = useState(false);
    const navigate = useNavigate();


    const onClickHandle = async (username:string, password:string) => {
        let userDto: UserDto = {
            username: name,
            password: password,
            email: null,
            role: null,
            referenceUserUsername: null,
        }
        const resp = await connectHandler.getInfo("/user", userDto)
        if (resp.status === 200) {
            setauthenticated(true)
            userDto = resp.data
            navigate("/main", {state: {user: userDto}})
        }
    }

    return (
        <div className={"grid-rows-1 gap-5 self-center content-center p-10  justify-centre justify-self-center bg-gray-500 w-2/3 h-2/3"}>

            <div><TextField value={name} setValue={setName} id={"username"} type={'text'} placeholder={'Username'}/></div>
            <div><TextField value={password} setValue={setPassword} id={"password"} type={'password'} placeholder={'Password'}/></div>
            <div onClick={(e:any) => onClickHandle(name, password)}><Button link={"/main"} id={"login"}  name={"Login"}/></div>


            <div>If you don't have an account then register</div>
            <div> <Button id={"register"} link={"/register"} name={"Register"}/></div>
        </div>
    )
}
export default HomePage;