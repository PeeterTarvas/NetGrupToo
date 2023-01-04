interface CatalogueDto {
    username: string;
    catalogueName: string;
    parent: string|bigint|null;
    catalogueId: bigint|null;

}
export default CatalogueDto;