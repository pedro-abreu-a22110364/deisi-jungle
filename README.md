![](diagrama.png?raw=true "Diagrama UML")

Efetuámos personalização ao nosso jogo, de forma que ele parecesse mais "retro".
Desta forma adicionámos personagens dos jogos clássicos, como o Mario, o Link, o fantasma do Pac Man e o Pikachu.
Também tentámos mudar as casas do tabuleiro através da função getSquareInfo, mas o DropProject falhava, então optámos por apenas adicionar os personagens.

![](src/images/retrostyle.png?raw=true "Estilo Retro")

No que toca à implementação dos rankings, usufruímos do bubble sort, aprendido em AED para ordenar dois arrays distintos, um de posições e outro de IDS.
Desta forma, quando há posições iguais, verificamos se no outro array os IDS estão na ordem correta, se não tiverem, trocamos, e por fim damos "set" no rank.
