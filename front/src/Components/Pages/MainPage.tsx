import UserDto from "../../Dtos/UserDto";
import React from "react";
import {useLocation} from "react-router-dom";
import userDto from "../../Dtos/UserDto";



const MainPage = () => {

    const location = useLocation();
    const user: userDto = location.state.user;


    return (
        <div className={"grid justify-items-stretch w-5/6 h-5/6 bg-teal-900 self-center justify-self-center"}>
            <div>{user.username}</div>
            <div>{user.role}</div>
            <div>{user.email}</div>


        </div>
    )
}
export default MainPage;