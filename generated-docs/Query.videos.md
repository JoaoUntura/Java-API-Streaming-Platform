# Query.videos: VideoConnection
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| input |  | Optional | CursorSearch |
            
## Example
```graphql
{
  videos(input: {first : 123456789, after : 123456789}) {
    videos
    endCursor
    hasNextPage
  }
}

```