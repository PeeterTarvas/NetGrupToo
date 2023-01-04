import TextField from "../Comps/TextField";
import React, {useEffect, useState} from "react";
import Button from "../Comps/Button";
import {useLocation, useNavigate} from "react-router-dom";
import UserDto from "../../Dtos/UserDto";
import connection from "../../Connect/ConnectHandler";
import CatalogueDto from "../../Dtos/CatalogueDto";
import {AxiosResponse} from "axios";
import userDto from "../../Dtos/UserDto";


const CatalogueForm = () => {
    let [catalogueName, setCatalogueName] = useState("")
    const navigate = useNavigate();
    const location = useLocation();
    const [parentCatalogue, setParentCatalogue] = useState(location.state.currentCatalogue);
    const [user, setUser] = useState<UserDto|undefined>();

    useEffect( () => {
        setUser(location.state.user);
        setCatalogueName(location.state.currentCatalogue);
    }, [location.state.currentCatalogue, location.state.user])

    const createCatalogue = async () => {
        const newCatalogue: CatalogueDto = {
            username: user!.username,
            catalogueName: catalogueName,
            parent: parentCatalogue.catalogueId,
            catalogueId: null
        }
        const resp = await connection.postInfo("/catalogue/create", newCatalogue)
        navigate("/main", {state: {
            user: user,
                catalogue: parentCatalogue,
        }})

    }


    return (
        <div className={"w-full h-full self-center justify-self-center"}>
            <div onClick={() =>navigate(-1)}><Button id={"Back"} name={"Back"}/></div>
            <div className={" p-8 w-5/6 h-5/6 bg-teal-900 self-center justify-self-center"}>
            <form>
                <div>
                    <TextField setNumberValue={undefined} value={catalogueName} setStringValue={setCatalogueName} id={"catalogueName"} type={'text'} placeholder={'Catalogue Name'}/>
                    <div onClick={(e) => {createCatalogue(); e.preventDefault()}}> <Button id={"register"} name={"Register"}/></div>
                </div>
            </form>
        </div>
            </div>

            )

}
export default CatalogueForm;