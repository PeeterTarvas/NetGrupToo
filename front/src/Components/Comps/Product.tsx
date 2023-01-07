import React from "react";
import ProductDto from "../../Dtos/ProductDto";


const Product:React.FC<ProductDto> = (props) => {

    return (
        <div className={'bg-blue-900 h-full justify-items-center content-center'}>
            <div>Name: {props.name}</div>
            <div>Serial number: {props.serialNumber}</div>
            <div>Picture: {props.pictureLink}</div>
            <div>Condition: {props.condition}</div>
            <div>Description: {props.description}</div>
            <div>Amount: {props.amount}</div>
        </div>
    )
}
export default Product;