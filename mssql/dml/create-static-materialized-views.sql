USE ldbc;

INSERT INTO [dbo].[Country] (id, name, url, PartOfContinentId)
    SELECT
        id,
        name,
        url,
        PartOfPlaceId AS PartOfContinentId
    FROM Place
    WHERE type = 'Country'
;

INSERT INTO [dbo].[City] (id, name, url, PartOfCountryId)
    SELECT
        id,
        name,
        url,
        PartOfPlaceId AS PartOfCountryId
    FROM Place
    WHERE type = 'City'
;

INSERT INTO [dbo].[Company] (id, name, url, LocationPlaceId)
    SELECT
        id,
        name,
        url,
        LocationPlaceId
    FROM Organisation
    WHERE type = 'Company'
;

INSERT INTO [dbo].[University] (id, name, url, LocationPlaceId)
    SELECT
        id,
        name,
        url,
        LocationPlaceId
    FROM Organisation
    WHERE type = 'University'
;
