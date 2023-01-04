import React from "react";
import {useNavigate} from "react-router-dom";
import UserDto from "../../Dtos/UserDto";
import connection from "../../Connect/ConnectHandler";
import userDto from "../../Dtos/UserDto";


interface CatalogueInterface {
    catalogueName: string,
    user: UserDto,
     onClickHandle(catalogueName: string, user: userDto): Promise<void>,
}


const Catalogue:React.FC<CatalogueInterface> = (props) => {
    const navigate = useNavigate();



    return (
        <div onClick={() => props.onClickHandle(props.catalogueName, props.user)} className={'bg-gray-500 h-full justify-items-center content-center hover:cursor-pointer'}>
            <div>{props.catalogueName}</div>
        </div>
    )

}
export default Catalogue;