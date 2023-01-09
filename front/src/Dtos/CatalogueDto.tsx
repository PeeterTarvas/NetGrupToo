/**
 * This is an interface object that is passed from front-end to back-end to send catalogue information.
 */
interface CatalogueDto {
    username: string;
    catalogueName: string;
    parent: string|bigint|null;
    catalogueId: bigint|null;

}
export default CatalogueDto;