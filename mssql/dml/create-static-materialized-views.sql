USE ldbc;

INSERT INTO [dbo].[Country] ($NODE_ID, id, name, url, PartOfContinentId)
    SELECT
        NODE_ID_FROM_PARTS(object_id('Country'), id) AS node_id,
        id,
        name,
        url,
        PartOfPlaceId AS PartOfContinentId
    FROM Place
    WHERE type = 'Country'
;

INSERT INTO [dbo].[City] ($NODE_ID, id, name, url, PartOfCountryId)
    SELECT
        NODE_ID_FROM_PARTS(object_id('City'), id) AS node_id,
        id,
        name,
        url,
        PartOfPlaceId AS PartOfCountryId
    FROM Place
    WHERE type = 'City'
;

INSERT INTO [dbo].[Company] ($NODE_ID, id, name, url, LocationPlaceId)
    SELECT
        NODE_ID_FROM_PARTS(object_id('Company'), id) AS node_id,
        id,
        name,
        url,
        LocationPlaceId
    FROM Organisation
    WHERE type = 'Company'
;

INSERT INTO [dbo].[University] ($NODE_ID, id, name, url, LocationPlaceId)
    SELECT
        NODE_ID_FROM_PARTS(object_id('University'), id) AS node_id,
        id,
        name,
        url,
        LocationPlaceId
    FROM Organisation
    WHERE type = 'University'
;
