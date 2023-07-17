# ashiura
YHdatabases-Project userWep,API


-- construction zone --


@GetMapping
public String theUniverseIsSingingToMe(
        @RequestBody SomeBodyDTO helpme, Model model)
        throws InterruptedException {
    boolean neet = true;
    while(neet) {
        log.info("fuck: {}","점심나가서먹을꺼같애")
        Thread.sleep(1000);
        if (helpme != null) {
            neet =false;
        }
    }
    return "/Bethesda/starfield"
}
