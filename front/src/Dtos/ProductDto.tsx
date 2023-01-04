interface ProductDto {
    name: string,
    serialNumber: string|undefined,
    pictureLink: string|undefined,
    productOwner: string,
    condition: string|undefined,
    description: string|undefined,
    amount: number|undefined
}
export default ProductDto;