import {useLocation, useNavigate} from "react-router-dom";
import Button from "../Comps/Button";
import TextField from "../Comps/TextField";
import React, {useState} from "react";
import {AxiosResponse} from "axios";
import connection from "../../Connect/ConnectHandler";
import ProductDto from "../../Dtos/ProductDto";
import UserDto from "../../Dtos/UserDto";
import CatalogueDto from "../../Dtos/CatalogueDto";

/**
 * This is the form component where user can register he's/her products.
 */
const ProductForm = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [productName, setProductName] = useState("");
    const [picture, setPicture] = useState("");
    const [serialNumber, setSerialNumber] = useState<string>("");
    const [condition, setCondition] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [amount, setAmount] = useState<number>(0);
    const user: UserDto = location.state.user
    const catalogue: CatalogueDto = (location.state.currentCatalogue);

    /**
     * This function creates a product based in input parameters to the user by sending a post request to back-end
     * to create the new product.
     */
    const createProduct = async () => {
        const newProduct: ProductDto = {
            name: productName,
            serialNumber: serialNumber,
            pictureLink: picture,
            productOwner: user.username,
            condition: condition,
            description: description,
            amount: amount
        }
        const resp: AxiosResponse<any> = await connection.postInfo(
            "/product/create/" + catalogue.catalogueId + "/" + user.username, newProduct)
        navigate("/main", {state: {
                user: user,
                catalogue: catalogue,
            }})
    }

    return (
        <div className={"grid w-full h-full self-center justify-self-center"}>
            <div onClick={() =>navigate(-1)}><Button id={"Back"} name={"Back"}/></div>
            <div className={" p-8 w-5/6 h-5/6 bg-teal-900 self-center justify-self-center"}>
                <form>
                    <div>
                        <TextField value={productName}  setNumberValue={undefined} setStringValue={setProductName} id={"catalogueName"} type={'text'} placeholder={'Name'}/>
                        <TextField value={picture} setNumberValue={undefined} setStringValue={setPicture} id={"picture"} type={'text'} placeholder={'Picture link'}/>
                        <TextField value={serialNumber} setNumberValue={undefined} setStringValue={setSerialNumber} id={"serialNumber"} type={'text'} placeholder={'Serial number'}/>
                        <TextField value={description} setNumberValue={undefined} setStringValue={setDescription} id={"description"} type={'text'} placeholder={'Description'}/>
                        <TextField value={amount} setNumberValue={setAmount} setStringValue={undefined} id={"amount"} type={'number'} placeholder={'Set amount'}/>
                        <div className={"grid grid-cols-3 "} role="group">
                            <button onClick={() => setCondition("GOOD")} type="button"
                                    className="py-2 px-4 text-sm font-medium text-gray-900 bg-transparent rounded-l-lg border border-gray-900 hover:bg-gray-900 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-gray-900 focus:text-white dark:border-white dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:bg-gray-700">
                                Good
                            </button>
                            <button  onClick={() => setCondition("BAD")} type="button"
                                    className="py-2 px-4 text-sm font-medium text-gray-900 bg-transparent border-t border-b border-gray-900 hover:bg-gray-900 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-gray-900 focus:text-white dark:border-white dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:bg-gray-700">
                                Bad
                            </button>
                            <button onClick={() => setCondition("BROKEN")} type="button"
                                    className="py-2 px-4 text-sm font-medium text-gray-900 bg-transparent rounded-r-md border border-gray-900 hover:bg-gray-900 hover:text-white focus:z-10 focus:ring-2 focus:ring-gray-500 focus:bg-gray-900 focus:text-white dark:border-white dark:text-white dark:hover:text-white dark:hover:bg-gray-700 dark:focus:bg-gray-700">
                                Broken
                            </button>
                        </div>
                        <div onClick={(e) => {createProduct(); e.preventDefault()}}> <Button id={"register"} name={"Register"}/></div>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default ProductForm;