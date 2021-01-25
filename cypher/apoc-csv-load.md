CALL apoc.import.csv(
  [{fileName: 'file:/Person.csv', labels: ['Person']}],
  [{fileName: 'file:/Person_knows_Person.csv', type: 'KNOWS'}],
  {delimiter: '|', arrayDelimiter: ';', stringIds: false}
)
