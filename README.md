# UYA Medius Tool

This tool can be used to manually encrypt/decrypt Ratchet & Clank 3: Up Your Arsenal
medius network packets for the PlayStation 2.

Ported over from the [original project](https://github.com/Dnawrkshp/uya-medius-encryption) in C#.

## Usage

CLI usage:

```bash
uyamediustool.jar <mode> -k [key] -m <message>
```
- **mode**: Can be `encrypt` or `decrypt`.
- **key**: Is the 64 hexstring key. (optional).
- **message**: Is whatever message you want to encrypt.

You can use this tool via the command-line directly, for example:

```bash
java -jar uyamediustool.jar decrypt -k 60937E5CD170EF0B5E0DF26DD93D84F04723CEDA8946886A329C8BE407D82EFADB383517D488448D5CA6F5D5F0204DC7BF5100528CE0373B7FDE1AA379D59486 -p 871700cdebc0747f0f52caad4b42ae74300f7d67bcd64f3eb158ed62a087
```

## Contribution
Huge thanks to [Dnawrkshp](https://github.com/Dnawrkshp) for reverse engineering the RC4 and RSA encryption.

