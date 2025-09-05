export interface JwtPayload {
  sub: string;
  username: string;
  roles: string[];
  exp: number;
}
