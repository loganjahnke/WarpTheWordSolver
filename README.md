# Warp the Word Puzzle Solver

This algorithm finds the shortest path from one word to another in the popular iOS Game **Warp the Word** by Surrogate Systems. For example, in the game you are required to "warp" one word into another by changing only one letter at a time while maintaining word validity. The **Warp the Word** dictionary has been provided. If the starting word is *CASE* and the ending word is *EASY*, the shortest path would be *CASE -> EASE -> EASY*.

This algorithm supports all dictionaries, so you can import your own dictionaries so long as they follow this naming convention -> **_letter_.txt**

## Compile

To run, `java Graph [start_word] [end_word]`.