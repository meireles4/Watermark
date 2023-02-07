fun compose(g: (Int) -> Int, h: (Int) -> Int): (Int) -> Int {
    return { i -> g(h(i)) }
}
