query_dir = 'queries'
graph = 'LDBC_SNB'

short_reads_names = ['interactiveShort1', 'interactiveShort2', 'interactiveShort3', 'interactiveShort4',
                     'interactiveShort5', 'interactiveShort6', 'interactiveShort7']

complex_names = [f'interactiveComplex{i}' for i in range(1, 15)]
print(complex_names)

query_names = short_reads_names + complex_names
queries = {name:f'{query_dir}/{name}.gsql' for (name) in query_names}


for name, file_path in queries.items():
    bash_statement = f'gsql --graph {graph} {file_path}'

    print(bash_statement)

print()

for name in queries:
    bash_statement = f'gsql --graph {graph} "INSTALL QUERY {name}"'

    print(bash_statement)
