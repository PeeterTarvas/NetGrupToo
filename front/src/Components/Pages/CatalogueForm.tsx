import TextField from "../Comps/TextField";
import React, {useEffect, useState} from "react";
import Button from "../Comps/Button";
import {useLocation, useNavigate} from "react-router-dom";
import UserDto from "../../Dtos/UserDto";
import connection from "../../Connect/ConnectHandler";
import CatalogueDto from "../../Dtos/CatalogueDto";


/**
 * This is a form component that is used to create new catalogues.
 */
const CatalogueForm = () => {
    let [catalogueName, setCatalogueName] = useState("")
    const navigate = useNavigate();
    const location = useLocation();
    const [parentCatalogue, setParentCatalogue] = useState(location.state.currentCatalogue);
    const [user, setUser] = useState<UserDto|undefined>();

    /**
     * When the component is initialized the user and current catalogue name is set.
     */
    useEffect( () => {
        setUser(location.state.user);
        setCatalogueName(location.state.currentCatalogue);
    }, [location.state.currentCatalogue, location.state.user])

    /**
     * This function creates a new catalogue out of the variables set in the text field,
     * then it sends that catalogue to back-end to be saved in the database.
     */
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
                    <TextField setNumberValue={undefined} value={catalogueName} setStringValue={setCatalogueName} id={"catalogueName"} type={'text'} placeholder={'Catalogue name'}/>
                    <div onClick={(e) => {createCatalogue(); e.preventDefault()}}> <Button id={"register"} name={"Register"}/></div>
                </div>
            </form>
        </div>
            </div>

            )

}
export default CatalogueForm;